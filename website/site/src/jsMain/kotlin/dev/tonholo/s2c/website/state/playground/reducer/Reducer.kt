package dev.tonholo.s2c.website.state.playground.reducer

interface Reducer<TAction, TState> {
    fun reduce(state: TState, action: TAction): TState
}
