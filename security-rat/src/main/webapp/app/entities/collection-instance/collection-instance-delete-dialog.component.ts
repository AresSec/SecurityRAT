import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICollectionInstance } from 'app/shared/model/collection-instance.model';
import { CollectionInstanceService } from './collection-instance.service';

@Component({
  templateUrl: './collection-instance-delete-dialog.component.html'
})
export class CollectionInstanceDeleteDialogComponent {
  collectionInstance?: ICollectionInstance;

  constructor(
    protected collectionInstanceService: CollectionInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.collectionInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('collectionInstanceListModification');
      this.activeModal.close();
    });
  }
}
