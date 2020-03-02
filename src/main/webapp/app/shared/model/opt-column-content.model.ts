import { IOptColumn } from 'app/shared/model/opt-column.model';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface IOptColumnContent {
  id?: number;
  content?: string;
  optColumn?: IOptColumn;
  requirementSkeleton?: IRequirementSkeleton;
}

export class OptColumnContent implements IOptColumnContent {
  constructor(
    public id?: number,
    public content?: string,
    public optColumn?: IOptColumn,
    public requirementSkeleton?: IRequirementSkeleton
  ) {}
}
