import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptColumn } from 'app/shared/model/opt-column.model';
import { OptColumnService } from './opt-column.service';
import { OptColumnDeleteDialogComponent } from './opt-column-delete-dialog.component';

@Component({
  selector: 'jhi-opt-column',
  templateUrl: './opt-column.component.html'
})
export class OptColumnComponent implements OnInit, OnDestroy {
  optColumns?: IOptColumn[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected optColumnService: OptColumnService,
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
      this.optColumnService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOptColumn[]>) => (this.optColumns = res.body || []));
      return;
    }

    this.optColumnService.query().subscribe((res: HttpResponse<IOptColumn[]>) => (this.optColumns = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOptColumns();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOptColumn): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOptColumns(): void {
    this.eventSubscriber = this.eventManager.subscribe('optColumnListModification', () => this.loadAll());
  }

  delete(optColumn: IOptColumn): void {
    const modalRef = this.modalService.open(OptColumnDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.optColumn = optColumn;
  }
}
