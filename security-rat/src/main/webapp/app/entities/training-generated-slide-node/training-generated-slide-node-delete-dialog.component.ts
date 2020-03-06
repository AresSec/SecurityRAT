import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';
import { TrainingGeneratedSlideNodeService } from './training-generated-slide-node.service';

@Component({
  templateUrl: './training-generated-slide-node-delete-dialog.component.html'
})
export class TrainingGeneratedSlideNodeDeleteDialogComponent {
  trainingGeneratedSlideNode?: ITrainingGeneratedSlideNode;

  constructor(
    protected trainingGeneratedSlideNodeService: TrainingGeneratedSlideNodeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.trainingGeneratedSlideNodeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('trainingGeneratedSlideNodeListModification');
      this.activeModal.close();
    });
  }
}
