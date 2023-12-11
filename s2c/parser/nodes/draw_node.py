from abc import ABC, abstractmethod
from s2c import isdebug

class DrawNode(ABC):
    should_close = False
    values = []
    is_relative = False

    @property
    @abstractmethod
    def command_name(self) -> str:
        pass

    def __init__(self, values: list[str], should_close = False, is_relative = False):
        self.values = values
        self.should_close = should_close
        self.is_relative = is_relative

    def __repr__(self) -> str:
        return f"{self.__class__}(should_close={self.should_close}, values={self.values}, is_relative={self.is_relative})"

    def _check_should_close(value: str):
        should_close = False
        current_value = value
        if (value.lower().endswith("z")):
            current_value = value.lower().removesuffix("z")
            should_close = True
        return current_value, should_close
    
    @abstractmethod
    def materialize(self) -> str:
        pass

class MoveToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)
        values = [first, commands.pop(0)]

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )

    @property
    def command_name(self) -> str:
        return "m"
    
    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""
        x = float(self.values[0])
        y = float(self.values[1])

        return f"""
                // {command}{" ".join(self.values)}
                moveTo{relative}({relative_prefix}x = {x}f, {relative_prefix}y = {y}f)
                {close_command}
""".lstrip()

class ArcToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)

        values = [first]
        for _ in range(1, 7):
            values.append(commands.pop(0))

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last
        
        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )

    @property
    def command_name(self) -> str:
        return "a"
    
    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""

        a = float(self.values[0])
        b = float(self.values[1])
        theta = float(self.values[2])
        is_more_than_half = "true" if self.values[3] == "1" else "false"
        is_positive_arc = "true" if self.values[4] == "1" else "false"
        x = float(self.values[5])
        y = float(self.values[6])

        return f"""
                // {command}{" ".join(self.values)}
                arcTo{relative}(
                    {"a" if self.is_relative else "horizontalEllipseRadius"} = {a}f,
                    {"b" if self.is_relative else "verticalEllipseRadius"} = {b}f,
                    theta = {theta}f,
                    isMoreThanHalf = {is_more_than_half},
                    isPositiveArc = {is_positive_arc},
                    {relative_prefix}x1 = {x}f, 
                    {relative_prefix}y1 = {y}f,
                )
                {close_command}
""".lstrip("\n")

class VerticalLineToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)

        first, should_close = DrawNode._check_should_close(value=first)

        values = [first]

        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    @property
    def command_name(self) -> str:
        return "v"

    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""
        return f"""
                // {command}{" ".join(self.values)}
                verticalLineTo{relative}({relative_prefix}y = {float(self.values[0])}f)
                {close_command}
""".lstrip("\n")

class HorizontalLineToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)

        first, should_close = DrawNode._check_should_close(value=first)

        values = [first]

        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )

    @property
    def command_name(self) -> str:
        return "h"
    
    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""
        return f"""
                // {command}{" ".join(self.values)}
                horizontalLineTo{relative}({relative_prefix}x = {float(self.values[0])}f)
                {close_command}
""".lstrip("\n")

class LineToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)

        values = [first, commands.pop(0)]

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )

    @property
    def command_name(self) -> str:
        return "l"
    
    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""
        x = float(self.values[0])
        y = float(self.values[1])
        return f"""
                // {command}{" ".join(self.values)}
                lineTo{relative}({relative_prefix}x = {x}f, {relative_prefix}y = {y}f)
                {close_command}
""".lstrip("\n")

class CurveToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)

        values = [first]
        for _ in range(1, 6):
            values.append(commands.pop(0))

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )

    @property
    def command_name(self) -> str:
        return "c"
    
    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""

        x1 = float(self.values[0])
        y1 = float(self.values[1])
        x2 = float(self.values[2])
        y2 = float(self.values[3])
        x3 = float(self.values[4])
        y3 = float(self.values[5])

        return f"""
                // {command}{" ".join(self.values)}
                curveTo{relative}(
                    {relative_prefix}x1 = {x1}f,
                    {relative_prefix}y1 = {y1}f,
                    {relative_prefix}x2 = {x2}f,
                    {relative_prefix}y2 = {y2}f,
                    {relative_prefix}x3 = {x3}f,
                    {relative_prefix}y3 = {y3}f,
                )
                {close_command}
""".lstrip("\n")

class ReflectiveCurveToDrawNode(DrawNode):
    def __init__(self, commands: list[str], is_relative: bool):
        first = commands.pop(0)

        first = first.lower().removeprefix(self.command_name)

        values = [first]
        for _ in range(1, 4):
            values.append(commands.pop(0))

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        if isdebug():
            print(f"Processing {self.command_name if is_relative else self.command_name.upper()}; values = {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )

    @property
    def command_name(self) -> str:
        return "s"
    
    def materialize(self) -> str:
        command = self.command_name if self.is_relative else self.command_name.upper()
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()" if self.should_close else ""

        x1 = float(self.values[0])
        y1 = float(self.values[1])
        x2 = float(self.values[2])
        y2 = float(self.values[3])

        return f"""
                // {command}{" ".join(self.values)}
                reflectiveCurveTo{relative}(
                    {relative_prefix}x1 = {x1}f,
                    {relative_prefix}y1 = {y1}f,
                    {relative_prefix}x2 = {x2}f,
                    {relative_prefix}y2 = {y2}f,
                )
                {close_command}
""".lstrip("\n")
