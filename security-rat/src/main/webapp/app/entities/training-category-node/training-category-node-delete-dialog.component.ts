import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingCategoryNode } from 'app/shared/model/training-category-node.model';
import { TrainingCategoryNodeService } from './training-category-node.service';

@Component({
  templateUrl: './training-category-node-delete-dialog.component.html'
})
export class TrainingCategoryNodeDeleteDialogComponent {
  trainingCategoryNode?: ITrainingCategoryNode;

  constructor(
    protected trainingCategoryNodeService: TrainingCategoryNodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingCategoryNodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingCategoryNodeListModification');
      this.activeModal.close();
    });
  }
}
