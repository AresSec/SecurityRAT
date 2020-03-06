import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollectionCategory } from 'app/shared/model/collection-category.model';
import { CollectionCategoryService } from './collection-category.service';

@Component({
  templateUrl: './collection-category-delete-dialog.component.html'
})
export class CollectionCategoryDeleteDialogComponent {
  collectionCategory?: ICollectionCategory;

  constructor(
    protected collectionCategoryService: CollectionCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collectionCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collectionCategoryListModification');
      this.activeModal.close();
    });
  }
}
