import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IRequirementSkeleton } from 'app/shared/model/requirement-skeleton.model';

type EntityResponseType = HttpResponse<IRequirementSkeleton>;
type EntityArrayResponseType = HttpResponse<IRequirementSkeleton[]>;

@Injectable({ providedIn: 'root' })
export class RequirementSkeletonService {
  public resourceUrl = SERVER_API_URL + 'api/requirement-skeletons';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/requirement-skeletons';

  constructor(protected http: HttpClient) {}

  create(requirementSkeleton: IRequirementSkeleton): Observable<EntityResponseType> {
    return this.http.post<IRequirementSkeleton>(this.resourceUrl, requirementSkeleton, { observe: 'response' });
  }

  update(requirementSkeleton: IRequirementSkeleton): Observable<EntityResponseType> {
    return this.http.put<IRequirementSkeleton>(this.resourceUrl, requirementSkeleton, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IRequirementSkeleton>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequirementSkeleton[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IRequirementSkeleton[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
