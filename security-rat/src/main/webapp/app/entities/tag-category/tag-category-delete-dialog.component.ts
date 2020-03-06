import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITagCategory } from 'app/shared/model/tag-category.model';
import { TagCategoryService } from './tag-category.service';

@Component({
  templateUrl: './tag-category-delete-dialog.component.html'
})
export class TagCategoryDeleteDialogComponent {
  tagCategory?: ITagCategory;

  constructor(
    protected tagCategoryService: TagCategoryService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tagCategoryService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tagCategoryListModification');
      this.activeModal.close();
    });
  }
}
