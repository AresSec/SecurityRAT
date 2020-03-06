import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface IReqCategory {
  id?: number;
  name?: string;
  shortcut?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  requirementSkeletons?: IRequirementSkeleton[];
}

export class ReqCategory implements IReqCategory {
  constructor(
    public id?: number,
    public name?: string,
    public shortcut?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public requirementSkeletons?: IRequirementSkeleton[]
  ) {
    this.active = this.active || false;
  }
}
