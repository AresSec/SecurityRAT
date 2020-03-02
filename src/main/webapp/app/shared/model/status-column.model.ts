import { IStatusColumnValue } from 'app/shared/model/status-column-value.model';
import { IProjectType } from 'app/shared/model/project-type.model';

export interface IStatusColumn {
  id?: number;
  name?: string;
  description?: string;
  isEnum?: boolean;
  showOrder?: number;
  active?: boolean;
  statusColumnValues?: IStatusColumnValue[];
  projectTypes?: IProjectType[];
}

export class StatusColumn implements IStatusColumn {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public isEnum?: boolean,
    public showOrder?: number,
    public active?: boolean,
    public statusColumnValues?: IStatusColumnValue[],
    public projectTypes?: IProjectType[]
  ) {
    this.isEnum = this.isEnum || false;
    this.active = this.active || false;
  }
}
