import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICollectionCategory } from 'app/shared/model/collection-category.model';

@Component({
  selector: 'jhi-collection-category-detail',
  templateUrl: './collection-category-detail.component.html'
})
export class CollectionCategoryDetailComponent implements OnInit {
  collectionCategory: ICollectionCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ collectionCategory }) => (this.collectionCategory = collectionCategory));
  }

  previousState(): void {
    window.history.back();
  }
}
