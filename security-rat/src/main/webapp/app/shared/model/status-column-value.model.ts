import { IStatusColumn } from 'app/shared/model/status-column.model';

export interface IStatusColumnValue {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  statusColumn?: IStatusColumn;
}

export class StatusColumnValue implements IStatusColumnValue {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public statusColumn?: IStatusColumn
  ) {
    this.active = this.active || false;
  }
}
