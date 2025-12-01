interface GameState {
  cells: Cell[];
  status: string;
}

interface Cell {
  state: string;
  x: number;
  y: number;
}

export type { GameState, Cell }