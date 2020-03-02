import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITrainingBranchNode } from 'app/shared/model/training-branch-node.model';

type EntityResponseType = HttpResponse<ITrainingBranchNode>;
type EntityArrayResponseType = HttpResponse<ITrainingBranchNode[]>;

@Injectable({ providedIn: 'root' })
export class TrainingBranchNodeService {
  public resourceUrl = SERVER_API_URL + 'api/training-branch-nodes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-branch-nodes';

  constructor(protected http: HttpClient) {}

  create(trainingBranchNode: ITrainingBranchNode): Observable<EntityResponseType> {
    return this.http.post<ITrainingBranchNode>(this.resourceUrl, trainingBranchNode, { observe: 'response' });
  }

  update(trainingBranchNode: ITrainingBranchNode): Observable<EntityResponseType> {
    return this.http.put<ITrainingBranchNode>(this.resourceUrl, trainingBranchNode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingBranchNode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingBranchNode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingBranchNode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
