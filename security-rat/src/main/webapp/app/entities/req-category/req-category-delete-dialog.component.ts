import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReqCategory } from 'app/shared/model/req-category.model';
import { ReqCategoryService } from './req-category.service';

@Component({
  templateUrl: './req-category-delete-dialog.component.html'
})
export class ReqCategoryDeleteDialogComponent {
  reqCategory?: IReqCategory;

  constructor(
    protected reqCategoryService: ReqCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.reqCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('reqCategoryListModification');
      this.activeModal.close();
    });
  }
}
