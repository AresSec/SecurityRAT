import { ITagInstance } from 'app/shared/model/tag-instance.model';

export interface ITagCategory {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  tagInstances?: ITagInstance[];
}

export class TagCategory implements ITagCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public tagInstances?: ITagInstance[]
  ) {
    this.active = this.active || false;
  }
}
