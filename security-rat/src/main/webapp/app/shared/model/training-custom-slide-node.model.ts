import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';

export interface ITrainingCustomSlideNode {
  id?: number;
  name?: string;
  content?: string;
  anchor?: number;
  node?: ITrainingTreeNode;
}

export class TrainingCustomSlideNode implements ITrainingCustomSlideNode {
  constructor(public id?: number, public name?: string, public content?: string, public anchor?: number, public node?: ITrainingTreeNode) {}
}
