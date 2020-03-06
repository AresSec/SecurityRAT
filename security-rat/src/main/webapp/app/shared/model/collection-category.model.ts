import { ICollectionInstance } from 'app/shared/model/collection-instance.model';

export interface ICollectionCategory {
  id?: number;
  name?: string;
  description?: string;
  showOrder?: number;
  active?: boolean;
  collectionInstances?: ICollectionInstance[];
}

export class CollectionCategory implements ICollectionCategory {
  constructor(
    public id?: number,
    public name?: string,
    public description?: string,
    public showOrder?: number,
    public active?: boolean,
    public collectionInstances?: ICollectionInstance[]
  ) {
    this.active = this.active || false;
  }
}
