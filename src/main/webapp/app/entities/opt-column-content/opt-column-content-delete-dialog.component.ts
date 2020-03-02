import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptColumnContent } from 'app/shared/model/opt-column-content.model';
import { OptColumnContentService } from './opt-column-content.service';

@Component({
  templateUrl: './opt-column-content-delete-dialog.component.html'
})
export class OptColumnContentDeleteDialogComponent {
  optColumnContent?: IOptColumnContent;

  constructor(
    protected optColumnContentService: OptColumnContentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.optColumnContentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('optColumnContentListModification');
      this.activeModal.close();
    });
  }
}
