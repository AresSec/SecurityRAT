import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';

export interface ITrainingBranchNode {
  id?: number;
  name?: string;
  anchor?: number;
  node?: ITrainingTreeNode;
}

export class TrainingBranchNode implements ITrainingBranchNode {
  constructor(public id?: number, public name?: string, public anchor?: number, public node?: ITrainingTreeNode) {}
}
