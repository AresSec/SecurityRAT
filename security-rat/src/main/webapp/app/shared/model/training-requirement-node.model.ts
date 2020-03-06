import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface ITrainingRequirementNode {
  id?: number;
  node?: ITrainingTreeNode;
  requirementSkeleton?: IRequirementSkeleton;
}

export class TrainingRequirementNode implements ITrainingRequirementNode {
  constructor(public id?: number, public node?: ITrainingTreeNode, public requirementSkeleton?: IRequirementSkeleton) {}
}
