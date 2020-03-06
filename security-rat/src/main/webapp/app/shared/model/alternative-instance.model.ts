import { IAlternativeSet } from 'app/shared/model/alternative-set.model';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface IAlternativeInstance {
  id?: number;
  content?: string;
  alternativeSet?: IAlternativeSet;
  requirementSkeleton?: IRequirementSkeleton;
}

export class AlternativeInstance implements IAlternativeInstance {
  constructor(
    public id?: number,
    public content?: string,
    public alternativeSet?: IAlternativeSet,
    public requirementSkeleton?: IRequirementSkeleton
  ) {}
}
