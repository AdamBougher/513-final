import React from 'react';
import './App.css'; // import the css file to enable your styles.
import { GameState, Cell } from './game';
import BoardCell from './Cell';

/**
 * Define the type of the props field for a React component
 */
interface Props { }

/**
 * Using generics to specify the type of props and state.
 * props and state is a special field in a React component.
 * React will keep track of the value of props and state.
 * Any time there's a change to their values, React will
 * automatically update (not fully re-render) the HTML needed.
 * 
 * props and state are similar in the sense that they manage
 * the data of this component. A change to their values will
 * cause the view (HTML) to change accordingly.
 * 
 * Usually, props is passed and changed by the parent component;
 * state is the internal value of the component and managed by
 * the component itself.
 */
class App extends React.Component<Props, GameState> {
  private initialized: boolean = false;

  /**
   * @param props has type Props
   */
  constructor(props: Props) {
    super(props)
    /**
     * state has type GameState as specified in the class inheritance.
     */
    this.state = { cells: [], status: '' };
  }

  /**
   * Use arrow function, i.e., () => {} to create an async function,
   * otherwise, 'this' would become undefined in runtime. This is
   * just an issue of Javascript.
   */
  newGame = async () => {
    const response = await fetch('/newgame');
    const json = await response.json();
    this.setState({ cells: json['cells'], status: json['status'] });
  }

  /**
   * Undo the last move.
   */
  undo = async () => {
    const response = await fetch('/undo');
    const json = await response.json();
    this.setState({ cells: json['cells'], status: json['status'] });
  }

  createCell(cell: Cell, index: number): React.ReactNode {
    return <BoardCell key={index} cell={cell} />;
  }

  /**
   * This function will call after the HTML is rendered.
   * We update the initial state by creating a new game.
   * @see https://reactjs.org/docs/react-component.html#componentdidmount
   */
  componentDidMount(): void {
    if (!this.initialized) {
      this.newGame();
      this.initialized = true;
    }
    // Add keyboard listener
    document.addEventListener('keydown', this.handleKeyPress);
  }

  /**
   * The only method you must define in a React.Component subclass.
   * @returns the React element via JSX.
   * @see https://reactjs.org/docs/react-component.html
   */
  render(): React.ReactNode {
    /**
     * We use JSX to define the template. An advantage of JSX is that you
     * can treat HTML elements as code.
     * @see https://reactjs.org/docs/introducing-jsx.html
     */
    
    return (
      <div>
        <div id="instructions">
          <p>{this.state.status}</p>
        </div>
        <div id="board">
          {this.state.cells.map((cell, i) => this.createCell(cell, i))}
        </div>
        <div id="bottombar">
          <button onClick={/* get the function, not call the function */this.newGame}>New Game</button>
          {/* Exercise: implement Undo function */}
          <button onClick={this.undo}>Undo</button>
        </div>
      </div>
    );
  }

  /**
   * Handle key press events.
   */
  handleKeyPress = async (e: KeyboardEvent) => {
    let dir: string | null = null;
    switch (e.key) {
      case 'ArrowUp':    dir = 'UP'; break;
      case 'ArrowDown':  dir = 'DOWN'; break;
      case 'ArrowLeft':  dir = 'LEFT'; break;
      case 'ArrowRight': dir = 'RIGHT'; break;
    }
    if (dir) {
      e.preventDefault();
      const response = await fetch(`/move?dir=${dir}`);
      const json = await response.json();
      this.setState({ cells: json['cells'], status: json['status'] });
    }
  }
}

export default App;
