import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { IReqCategory } from 'app/shared/model/req-category.model';

export interface ITrainingCategoryNode {
  id?: number;
  name?: string;
  node?: ITrainingTreeNode;
  category?: IReqCategory;
}

export class TrainingCategoryNode implements ITrainingCategoryNode {
  constructor(public id?: number, public name?: string, public node?: ITrainingTreeNode, public category?: IReqCategory) {}
}
