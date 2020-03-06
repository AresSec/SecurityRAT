import { IOptColumnType } from 'app/shared/model/opt-column-type.model';
import { IAlternativeSet } from 'app/shared/model/alternative-set.model';
import { IOptColumnContent } from 'app/shared/model/opt-column-content.model';
import { IProjectType } from 'app/shared/model/project-type.model';

export interface IOptColumn {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  isVisibleByDefault?: boolean;
  optColumnType?: IOptColumnType;
  alternativeSets?: IAlternativeSet[];
  optColumnContents?: IOptColumnContent[];
  projectTypes?: IProjectType[];
}

export class OptColumn implements IOptColumn {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public isVisibleByDefault?: boolean,
    public optColumnType?: IOptColumnType,
    public alternativeSets?: IAlternativeSet[],
    public optColumnContents?: IOptColumnContent[],
    public projectTypes?: IProjectType[]
  ) {
    this.active = this.active || false;
    this.isVisibleByDefault = this.isVisibleByDefault || false;
  }
}
