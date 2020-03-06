import { IOptColumn } from 'app/shared/model/opt-column.model';
import { IAlternativeInstance } from 'app/shared/model/alternative-instance.model';

export interface IAlternativeSet {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  optColumn?: IOptColumn;
  alternativeInstances?: IAlternativeInstance[];
}

export class AlternativeSet implements IAlternativeSet {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public optColumn?: IOptColumn,
    public alternativeInstances?: IAlternativeInstance[]
  ) {
    this.active = this.active || false;
  }
}
