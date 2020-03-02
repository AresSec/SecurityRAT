import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrainingCustomSlideNode, TrainingCustomSlideNode } from 'app/shared/model/training-custom-slide-node.model';
import { TrainingCustomSlideNodeService } from './training-custom-slide-node.service';
import { TrainingCustomSlideNodeComponent } from './training-custom-slide-node.component';
import { TrainingCustomSlideNodeDetailComponent } from './training-custom-slide-node-detail.component';
import { TrainingCustomSlideNodeUpdateComponent } from './training-custom-slide-node-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingCustomSlideNodeResolve implements Resolve<ITrainingCustomSlideNode> {
  constructor(private service: TrainingCustomSlideNodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingCustomSlideNode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trainingCustomSlideNode: HttpResponse<TrainingCustomSlideNode>) => {
          if (trainingCustomSlideNode.body) {
            return of(trainingCustomSlideNode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingCustomSlideNode());
  }
}

export const trainingCustomSlideNodeRoute: Routes = [
  {
    path: '',
    component: TrainingCustomSlideNodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCustomSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrainingCustomSlideNodeDetailComponent,
    resolve: {
      trainingCustomSlideNode: TrainingCustomSlideNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCustomSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrainingCustomSlideNodeUpdateComponent,
    resolve: {
      trainingCustomSlideNode: TrainingCustomSlideNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCustomSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrainingCustomSlideNodeUpdateComponent,
    resolve: {
      trainingCustomSlideNode: TrainingCustomSlideNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingCustomSlideNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
