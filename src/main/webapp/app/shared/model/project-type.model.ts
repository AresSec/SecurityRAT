import { IStatusColumn } from 'app/shared/model/status-column.model';
import { IOptColumn } from 'app/shared/model/opt-column.model';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface IProjectType {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  statusColumns?: IStatusColumn[];
  optColumns?: IOptColumn[];
  requirementSkeletons?: IRequirementSkeleton[];
}

export class ProjectType implements IProjectType {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public statusColumns?: IStatusColumn[],
    public optColumns?: IOptColumn[],
    public requirementSkeletons?: IRequirementSkeleton[]
  ) {
    this.active = this.active || false;
  }
}
