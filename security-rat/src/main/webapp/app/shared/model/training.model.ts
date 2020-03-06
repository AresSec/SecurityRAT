import { IOptColumn } from 'app/shared/model/opt-column.model';
import { ICollectionInstance } from 'app/shared/model/collection-instance.model';
import { IProjectType } from 'app/shared/model/project-type.model';

export interface ITraining {
  id?: number;
  name?: string;
  description?: string;
  allRequirementsSelected?: boolean;
  optColumns?: IOptColumn[];
  collections?: ICollectionInstance[];
  projectTypes?: IProjectType[];
}

export class Training implements ITraining {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public allRequirementsSelected?: boolean,
    public optColumns?: IOptColumn[],
    public collections?: ICollectionInstance[],
    public projectTypes?: IProjectType[]
  ) {
    this.allRequirementsSelected = this.allRequirementsSelected || false;
  }
}
