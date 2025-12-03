import React from 'react';
import { Cell } from './game';

interface Props {
  cell: Cell
}

class BoardCell extends React.Component<Props> {
  render(): React.ReactNode {
    let content = null;

    switch (this.props.cell.state) {
      case 'ISLAND':
        content = <img src="island.jpg" alt="Island" className="sprite" />;
        break;
      case 'SHIP':
        content = <img src="ship.png" alt="Ship" className="sprite" />;
        break;
      case 'PIRATE':
        content = <img src="pirateShip.png" alt="Pirate" className="sprite" />;
        break;
      case 'TREASURE':
        content = <img src="Treasure-chest.png" alt="Treasure" className="sprite" />;
        break;
      case 'SEA_MONSTER':
        content = <img src="monster.png" alt="Sea Monster" className="sprite" />;
        break;
    }

    return <div className={`cell ${this.props.cell.state.toLowerCase()}`}>{content}</div>;
  }
}

export default BoardCell;