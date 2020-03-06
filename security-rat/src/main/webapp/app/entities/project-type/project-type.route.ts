import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProjectType, ProjectType } from 'app/shared/model/project-type.model';
import { ProjectTypeService } from './project-type.service';
import { ProjectTypeComponent } from './project-type.component';
import { ProjectTypeDetailComponent } from './project-type-detail.component';
import { ProjectTypeUpdateComponent } from './project-type-update.component';

@Injectable({ providedIn: 'root' })
export class ProjectTypeResolve implements Resolve<IProjectType> {
  constructor(private service: ProjectTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProjectType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((projectType: HttpResponse<ProjectType>) => {
          if (projectType.body) {
            return of(projectType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProjectType());
  }
}

export const projectTypeRoute: Routes = [
  {
    path: '',
    component: ProjectTypeComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.projectType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProjectTypeDetailComponent,
    resolve: {
      projectType: ProjectTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.projectType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProjectTypeUpdateComponent,
    resolve: {
      projectType: ProjectTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.projectType.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProjectTypeUpdateComponent,
    resolve: {
      projectType: ProjectTypeResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'securityRatApp.projectType.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
