import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISlideTemplate } from 'app/shared/model/slide-template.model';
import { SlideTemplateService } from './slide-template.service';
import { SlideTemplateDeleteDialogComponent } from './slide-template-delete-dialog.component';

@Component({
  selector: 'jhi-slide-template',
  templateUrl: './slide-template.component.html'
})
export class SlideTemplateComponent implements OnInit, OnDestroy {
  slideTemplates?: ISlideTemplate[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected slideTemplateService: SlideTemplateService,
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
      this.slideTemplateService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISlideTemplate[]>) => (this.slideTemplates = res.body || []));
      return;
    }

    this.slideTemplateService.query().subscribe((res: HttpResponse<ISlideTemplate[]>) => (this.slideTemplates = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSlideTemplates();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISlideTemplate): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSlideTemplates(): void {
    this.eventSubscriber = this.eventManager.subscribe('slideTemplateListModification', () => this.loadAll());
  }

  delete(slideTemplate: ISlideTemplate): void {
    const modalRef = this.modalService.open(SlideTemplateDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.slideTemplate = slideTemplate;
  }
}
