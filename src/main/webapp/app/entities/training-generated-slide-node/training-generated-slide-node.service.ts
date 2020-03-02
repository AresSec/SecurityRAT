import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ITrainingGeneratedSlideNode } from 'app/shared/model/training-generated-slide-node.model';

type EntityResponseType = HttpResponse<ITrainingGeneratedSlideNode>;
type EntityArrayResponseType = HttpResponse<ITrainingGeneratedSlideNode[]>;

@Injectable({ providedIn: 'root' })
export class TrainingGeneratedSlideNodeService {
  public resourceUrl = SERVER_API_URL + 'api/training-generated-slide-nodes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/training-generated-slide-nodes';

  constructor(protected http: HttpClient) {}

  create(trainingGeneratedSlideNode: ITrainingGeneratedSlideNode): Observable<EntityResponseType> {
    return this.http.post<ITrainingGeneratedSlideNode>(this.resourceUrl, trainingGeneratedSlideNode, { observe: 'response' });
  }

  update(trainingGeneratedSlideNode: ITrainingGeneratedSlideNode): Observable<EntityResponseType> {
    return this.http.put<ITrainingGeneratedSlideNode>(this.resourceUrl, trainingGeneratedSlideNode, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITrainingGeneratedSlideNode>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingGeneratedSlideNode[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITrainingGeneratedSlideNode[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
