import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollectionInstance } from 'app/shared/model/collection-instance.model';

@Component({
  selector: 'jhi-collection-instance-detail',
  templateUrl: './collection-instance-detail.component.html'
})
export class CollectionInstanceDetailComponent implements OnInit {
  collectionInstance: ICollectionInstance | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collectionInstance }) => (this.collectionInstance = collectionInstance));
  }

  previousState(): void {
    window.history.back();
  }
}
