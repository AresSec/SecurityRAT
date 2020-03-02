import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptColumnType } from 'app/shared/model/opt-column-type.model';
import { OptColumnTypeService } from './opt-column-type.service';

@Component({
  templateUrl: './opt-column-type-delete-dialog.component.html'
})
export class OptColumnTypeDeleteDialogComponent {
  optColumnType?: IOptColumnType;

  constructor(
    protected optColumnTypeService: OptColumnTypeService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optColumnTypeService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optColumnTypeListModification');
      this.activeModal.close();
    });
  }
}
