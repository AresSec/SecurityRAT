import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IReqCategory } from 'app/shared/model/req-category.model';
import { ReqCategoryService } from './req-category.service';
import { ReqCategoryDeleteDialogComponent } from './req-category-delete-dialog.component';

@Component({
  selector: 'jhi-req-category',
  templateUrl: './req-category.component.html'
})
export class ReqCategoryComponent implements OnInit, OnDestroy {
  reqCategories?: IReqCategory[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected reqCategoryService: ReqCategoryService,
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
      this.reqCategoryService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IReqCategory[]>) => (this.reqCategories = res.body || []));
      return;
    }

    this.reqCategoryService.query().subscribe((res: HttpResponse<IReqCategory[]>) => (this.reqCategories = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInReqCategories();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IReqCategory): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInReqCategories(): void {
    this.eventSubscriber = this.eventManager.subscribe('reqCategoryListModification', () => this.loadAll());
  }

  delete(reqCategory: IReqCategory): void {
    const modalRef = this.modalService.open(ReqCategoryDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.reqCategory = reqCategory;
  }
}
