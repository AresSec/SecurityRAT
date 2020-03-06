import { ICollectionCategory } from 'app/shared/model/collection-category.model';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

export interface ICollectionInstance {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  collectionCategory?: ICollectionCategory;
  requirementSkeletons?: IRequirementSkeleton[];
}

export class CollectionInstance implements ICollectionInstance {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public collectionCategory?: ICollectionCategory,
    public requirementSkeletons?: IRequirementSkeleton[]
  ) {
    this.active = this.active || false;
  }
}
