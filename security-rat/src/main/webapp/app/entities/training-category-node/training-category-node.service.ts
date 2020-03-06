import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITrainingCategoryNode } from 'app/shared/model/training-category-node.model';

type EntityResponseType = HttpResponse<ITrainingCategoryNode>;
type EntityArrayResponseType = HttpResponse<ITrainingCategoryNode[]>;

@Injectable({ providedIn: 'root' })
export class TrainingCategoryNodeService {
  public resourceUrl = SERVER_API_URL + 'api/training-category-nodes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-category-nodes';

  constructor(protected http: HttpClient) {}

  create(trainingCategoryNode: ITrainingCategoryNode): Observable<EntityResponseType> {
    return this.http.post<ITrainingCategoryNode>(this.resourceUrl, trainingCategoryNode, { observe: 'response' });
  }

  update(trainingCategoryNode: ITrainingCategoryNode): Observable<EntityResponseType> {
    return this.http.put<ITrainingCategoryNode>(this.resourceUrl, trainingCategoryNode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingCategoryNode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingCategoryNode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingCategoryNode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
