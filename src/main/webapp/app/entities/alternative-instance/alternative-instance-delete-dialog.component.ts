import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlternativeInstance } from 'app/shared/model/alternative-instance.model';
import { AlternativeInstanceService } from './alternative-instance.service';

@Component({
  templateUrl: './alternative-instance-delete-dialog.component.html'
})
export class AlternativeInstanceDeleteDialogComponent {
  alternativeInstance?: IAlternativeInstance;

  constructor(
    protected alternativeInstanceService: AlternativeInstanceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alternativeInstanceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alternativeInstanceListModification');
      this.activeModal.close();
    });
  }
}
