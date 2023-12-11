from abc import ABC, abstractmethod

class DrawNode(ABC):
    should_close = False
    values = []
    is_relative = False

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
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("m")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("m")
        first = first.lower().removeprefix("m")
        values = [first, commands.pop(0)]

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        print(f"// {values}")

        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "m" if self.is_relative else "M"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""
        x = float(self.values[0])
        y = float(self.values[1])

        return f"""
                // {command}{" ".join(self.values)}
                moveTo{relative}({relative_prefix}x = {x}f, {relative_prefix}y = {y}f)
                {close_command}
"""

class ArcToDrawNode(DrawNode):
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("a")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("a")
        first = first.lower().removeprefix("a")

        values = [first]
        for _ in range(1, 7):
            values.append(commands.pop(0))

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last
        
        print(f"// {values}")
        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "a" if self.is_relative else "A"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""

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
                    {"a" if self.is_relative else "horizontalEllipseRadius"} = ${a}f,
                    {"b" if self.is_relative else "verticalEllipseRadius"} = ${b}f,
                    theta = ${theta}f,
                    isMoreThanHalf = {is_more_than_half},
                    isPositiveArc = {is_positive_arc},
                    {relative_prefix}x = {x}f, 
                    {relative_prefix}y = {y}f,
                )
                {close_command}
"""

class VerticalLineToDrawNode(DrawNode):
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("v")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("v")
        first = first.lower().removeprefix("v")

        first, should_close = DrawNode._check_should_close(value=first)

        values = [first]

        print(f"// {values}")
        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "v" if self.is_relative else "V"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""
        return f"""
                // {command}{" ".join(self.values)}
                verticalLineTo{relative}({relative_prefix}y = {float(self.values[0])}f)
                {close_command}
"""

class HorizontalLineToDrawNode(DrawNode):
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("h")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("h")
        first = first.lower().removeprefix("h")

        first, should_close = DrawNode._check_should_close(value=first)

        values = [first]

        print(f"// {values}")
        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "h" if self.is_relative else "H"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""
        return f"""
                // {command}{" ".join(self.values)}
                horizontalLineTo{relative}({relative_prefix}x = {float(self.values[0])}f)
                {close_command}
"""

class LineToDrawNode(DrawNode):
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("l")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("l")
        first = first.lower().removeprefix("l")

        values = [first, commands.pop(0)]

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        print(f"// {values}")
        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "l" if self.is_relative else "L"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""
        x = float(self.values[0])
        y = float(self.values[1])
        return f"""
                // {command}{" ".join(self.values)}
                lineTo{relative}({relative_prefix}x = {x}f, {relative_prefix}y = {y}f)
                {close_command}
"""

class CurveToDrawNode(DrawNode):
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("c")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("c")
        first = first.lower().removeprefix("c")

        values = [first]
        for _ in range(1, 6):
            values.append(commands.pop(0))

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        print(f"// {values}")
        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "c" if self.is_relative else "C"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""

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
"""

class ReflectiveCurveToDrawNode(DrawNode):
    def __init__(self, commands: list[str]):
        first = commands.pop(0)
        if (not first.lower().startswith("s")):
            raise Exception("TODO: Wrong start path. Expecting to start with M or m. Current path={path}")
        
        is_relative = first.startswith("s")
        first = first.lower().removeprefix("s")

        values = [first]
        for _ in range(1, 4):
            values.append(commands.pop(0))

        last = values[-1]
        last, should_close = DrawNode._check_should_close(value=last)
        values[-1] = last

        print(f"// {values}")
        DrawNode.__init__(
            self=self,
            values = values,
            should_close=should_close,
            is_relative = is_relative,
        )
    
    def materialize(self) -> str:
        command = "a" if self.is_relative else "A"
        relative = "Relative" if self.is_relative else ""
        relative_prefix = "d" if self.is_relative else ""
        close_command = "close()\n" if self.should_close else ""

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
"""
