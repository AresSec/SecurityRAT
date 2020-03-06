import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { IOptColumn } from 'app/shared/model/opt-column.model';

export interface ITrainingGeneratedSlideNode {
  id?: number;
  node?: ITrainingTreeNode;
  optColumn?: IOptColumn;
}

export class TrainingGeneratedSlideNode implements ITrainingGeneratedSlideNode {
  constructor(public id?: number, public node?: ITrainingTreeNode, public optColumn?: IOptColumn) {}
}
