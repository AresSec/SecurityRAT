import { ITagCategory } from 'app/shared/model/tag-category.model';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface ITagInstance {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  tagCategory?: ITagCategory;
  requirementSkeletons?: IRequirementSkeleton[];
}

export class TagInstance implements ITagInstance {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public tagCategory?: ITagCategory,
    public requirementSkeletons?: IRequirementSkeleton[]
  ) {
    this.active = this.active || false;
  }
}
