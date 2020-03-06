import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITagCategory } from 'app/shared/model/tag-category.model';
import { TagCategoryService } from './tag-category.service';
import { TagCategoryDeleteDialogComponent } from './tag-category-delete-dialog.component';

@Component({
  selector: 'jhi-tag-category',
  templateUrl: './tag-category.component.html'
})
export class TagCategoryComponent implements OnInit, OnDestroy {
  tagCategories?: ITagCategory[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected tagCategoryService: TagCategoryService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.tagCategoryService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITagCategory[]>) => (this.tagCategories = res.body || []));
      return;
    }

    this.tagCategoryService.query().subscribe((res: HttpResponse<ITagCategory[]>) => (this.tagCategories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTagCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITagCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTagCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('tagCategoryListModification', () => this.loadAll());
  }

  delete(tagCategory: ITagCategory): void {
    const modalRef = this.modalService.open(TagCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tagCategory = tagCategory;
  }
}
