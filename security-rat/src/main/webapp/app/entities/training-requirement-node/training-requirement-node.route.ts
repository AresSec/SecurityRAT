import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITrainingRequirementNode, TrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';
import { TrainingRequirementNodeService } from './training-requirement-node.service';
import { TrainingRequirementNodeComponent } from './training-requirement-node.component';
import { TrainingRequirementNodeDetailComponent } from './training-requirement-node-detail.component';
import { TrainingRequirementNodeUpdateComponent } from './training-requirement-node-update.component';

@Injectable({ providedIn: 'root' })
export class TrainingRequirementNodeResolve implements Resolve<ITrainingRequirementNode> {
  constructor(private service: TrainingRequirementNodeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITrainingRequirementNode> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((trainingRequirementNode: HttpResponse<TrainingRequirementNode>) => {
          if (trainingRequirementNode.body) {
            return of(trainingRequirementNode.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TrainingRequirementNode());
  }
}

export const trainingRequirementNodeRoute: Routes = [
  {
    path: '',
    component: TrainingRequirementNodeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingRequirementNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TrainingRequirementNodeDetailComponent,
    resolve: {
      trainingRequirementNode: TrainingRequirementNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingRequirementNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TrainingRequirementNodeUpdateComponent,
    resolve: {
      trainingRequirementNode: TrainingRequirementNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingRequirementNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TrainingRequirementNodeUpdateComponent,
    resolve: {
      trainingRequirementNode: TrainingRequirementNodeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.trainingRequirementNode.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
