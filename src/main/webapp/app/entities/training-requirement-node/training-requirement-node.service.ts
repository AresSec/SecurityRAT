import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITrainingRequirementNode } from 'app/shared/model/training-requirement-node.model';

type EntityResponseType = HttpResponse<ITrainingRequirementNode>;
type EntityArrayResponseType = HttpResponse<ITrainingRequirementNode[]>;

@Injectable({ providedIn: 'root' })
export class TrainingRequirementNodeService {
  public resourceUrl = SERVER_API_URL + 'api/training-requirement-nodes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-requirement-nodes';

  constructor(protected http: HttpClient) {}

  create(trainingRequirementNode: ITrainingRequirementNode): Observable<EntityResponseType> {
    return this.http.post<ITrainingRequirementNode>(this.resourceUrl, trainingRequirementNode, { observe: 'response' });
  }

  update(trainingRequirementNode: ITrainingRequirementNode): Observable<EntityResponseType> {
    return this.http.put<ITrainingRequirementNode>(this.resourceUrl, trainingRequirementNode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingRequirementNode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingRequirementNode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingRequirementNode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
