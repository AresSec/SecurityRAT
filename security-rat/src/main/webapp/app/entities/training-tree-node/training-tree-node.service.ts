import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITrainingTreeNode } from 'app/shared/model/training-tree-node.model';

type EntityResponseType = HttpResponse<ITrainingTreeNode>;
type EntityArrayResponseType = HttpResponse<ITrainingTreeNode[]>;

@Injectable({ providedIn: 'root' })
export class TrainingTreeNodeService {
  public resourceUrl = SERVER_API_URL + 'api/training-tree-nodes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-tree-nodes';

  constructor(protected http: HttpClient) {}

  create(trainingTreeNode: ITrainingTreeNode): Observable<EntityResponseType> {
    return this.http.post<ITrainingTreeNode>(this.resourceUrl, trainingTreeNode, { observe: 'response' });
  }

  update(trainingTreeNode: ITrainingTreeNode): Observable<EntityResponseType> {
    return this.http.put<ITrainingTreeNode>(this.resourceUrl, trainingTreeNode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingTreeNode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingTreeNode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingTreeNode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
