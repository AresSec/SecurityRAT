import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';
import { TrainingCustomSlideNodeService } from './training-custom-slide-node.service';
import { TrainingCustomSlideNodeDeleteDialogComponent } from './training-custom-slide-node-delete-dialog.component';

@Component({
  selector: 'jhi-training-custom-slide-node',
  templateUrl: './training-custom-slide-node.component.html'
})
export class TrainingCustomSlideNodeComponent implements OnInit, OnDestroy {
  trainingCustomSlideNodes?: ITrainingCustomSlideNode[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected trainingCustomSlideNodeService: TrainingCustomSlideNodeService,
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
      this.trainingCustomSlideNodeService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ITrainingCustomSlideNode[]>) => (this.trainingCustomSlideNodes = res.body || []));
      return;
    }

    this.trainingCustomSlideNodeService
      .query()
      .subscribe((res: HttpResponse<ITrainingCustomSlideNode[]>) => (this.trainingCustomSlideNodes = res.body || []));
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTrainingCustomSlideNodes();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITrainingCustomSlideNode): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTrainingCustomSlideNodes(): void {
    this.eventSubscriber = this.eventManager.subscribe('trainingCustomSlideNodeListModification', () => this.loadAll());
  }

  delete(trainingCustomSlideNode: ITrainingCustomSlideNode): void {
    const modalRef = this.modalService.open(TrainingCustomSlideNodeDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.trainingCustomSlideNode = trainingCustomSlideNode;
  }
}
