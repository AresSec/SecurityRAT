import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOptColumnType } from 'app/shared/model/opt-column-type.model';
import { OptColumnTypeService } from './opt-column-type.service';
import { OptColumnTypeDeleteDialogComponent } from './opt-column-type-delete-dialog.component';

@Component({
  selector: 'jhi-opt-column-type',
  templateUrl: './opt-column-type.component.html'
})
export class OptColumnTypeComponent implements OnInit, OnDestroy {
  optColumnTypes?: IOptColumnType[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected optColumnTypeService: OptColumnTypeService,
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
      this.optColumnTypeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<IOptColumnType[]>) => (this.optColumnTypes = res.body || []));
      return;
    }

    this.optColumnTypeService.query().subscribe((res: HttpResponse<IOptColumnType[]>) => (this.optColumnTypes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOptColumnTypes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOptColumnType): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOptColumnTypes(): void {
    this.eventSubscriber = this.eventManager.subscribe('optColumnTypeListModification', () => this.loadAll());
  }

  delete(optColumnType: IOptColumnType): void {
    const modalRef = this.modalService.open(OptColumnTypeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.optColumnType = optColumnType;
  }
}
