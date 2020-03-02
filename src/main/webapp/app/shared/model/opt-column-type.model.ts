import { IOptColumn } from 'app/shared/model/opt-column.model';

export interface IOptColumnType {
  id?: number;
  name?: string;
  description?: string;
  optColumns?: IOptColumn[];
}

export class OptColumnType implements IOptColumnType {
  constructor(public id?: number, public name?: string, public description?: string, public optColumns?: IOptColumn[]) {}
}
