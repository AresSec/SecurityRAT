import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAlternativeSet } from 'app/shared/model/alternative-set.model';
import { AlternativeSetService } from './alternative-set.service';

@Component({
  templateUrl: './alternative-set-delete-dialog.component.html'
})
export class AlternativeSetDeleteDialogComponent {
  alternativeSet?: IAlternativeSet;

  constructor(
    protected alternativeSetService: AlternativeSetService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.alternativeSetService.delete(id).subscribe(() => {
      this.eventManager.broadcast('alternativeSetListModification');
      this.activeModal.close();
    });
  }
}
