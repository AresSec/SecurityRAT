import { IOptColumnContent } from 'app/shared/model/opt-column-content.model';
import { IAlternativeInstance } from 'app/shared/model/alternative-instance.model';
import { IReqCategory } from 'app/shared/model/req-category.model';
import { ITagInstance } from 'app/shared/model/tag-instance.model';
import { ICollectionInstance } from 'app/shared/model/collection-instance.model';
import { IProjectType } from 'app/shared/model/project-type.model';

export interface IRequirementSkeleton {
  id?: number;
  universalId?: string;
  shortName?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  optColumnContents?: IOptColumnContent[];
  alternativeInstances?: IAlternativeInstance[];
  reqCategory?: IReqCategory;
  tagInstances?: ITagInstance[];
  collectionInstances?: ICollectionInstance[];
  projectTypes?: IProjectType[];
}

export class RequirementSkeleton implements IRequirementSkeleton {
  constructor(
    public id?: number,
    public universalId?: string,
    public shortName?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public optColumnContents?: IOptColumnContent[],
    public alternativeInstances?: IAlternativeInstance[],
    public reqCategory?: IReqCategory,
    public tagInstances?: ITagInstance[],
    public collectionInstances?: ICollectionInstance[],
    public projectTypes?: IProjectType[]
  ) {
    this.active = this.active || false;
  }
}
