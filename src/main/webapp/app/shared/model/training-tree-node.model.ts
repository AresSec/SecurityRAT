import { ITraining } from 'app/shared/model/training.model';
import { TrainingTreeNodeType } from 'app/shared/model/enumerations/training-tree-node-type.model';

export interface ITrainingTreeNode {
  id?: number;
  nodeType?: TrainingTreeNodeType;
  sortOrder?: number;
  active?: boolean;
  parentId?: ITrainingTreeNode;
  trainingId?: ITraining;
}

export class TrainingTreeNode implements ITrainingTreeNode {
  constructor(
    public id?: number,
    public nodeType?: TrainingTreeNodeType,
    public sortOrder?: number,
    public active?: boolean,
    public parentId?: ITrainingTreeNode,
    public trainingId?: ITraining
  ) {
    this.active = this.active || false;
  }
}
