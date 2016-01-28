package computations.predictor.ml.solver;

import java.util.ArrayList;
import java.util.List;

import computations.Constants;
import computations.predictor.OutcomeStatistics;
import computations.predictor.ml.PredictorML;
import computations.predictor.ml.model.DataRecord;
import logger.Logger;
import servlets.SessionNotReadyException;

public class FixedMostRecentMeasuresSolver implements PredictorSolver
{
	public int predict(PredictorML predictor, List<Double> ballLapTimes, List<Double> wheelLapTimes, String sessionId) throws SessionNotReadyException
	{
		List<DataRecord> predictRecords = getPredictedRecords(predictor, ballLapTimes, wheelLapTimes, sessionId);

		List<Integer> mostProbableNumberList = new ArrayList<>();
		for (int i = 0; i < predictRecords.size(); i++)
		{
			DataRecord predictRecord = predictRecords.get(i);
			Logger.traceINFO("(" + i + ") Record to predict : " + predictRecord);
			int mostProbableNumber = DataRecord.predictOutcome(predictRecord);
			Logger.traceINFO("(" + i + ") Most probable number : " + mostProbableNumber);
			mostProbableNumberList.add(mostProbableNumber);
		}

		int size = mostProbableNumberList.size();
		int par = Constants.RECORDS_COUNT_FOR_PREDICTION;
		if (size >= par)
		{
			mostProbableNumberList = mostProbableNumberList.subList(size - par, size);
		}

		OutcomeStatistics os = OutcomeStatistics.create(mostProbableNumberList);
		int mostProbableFinalNumber = os.meanNumber;
		Logger.traceINFO("(final) Most probable number : " + mostProbableFinalNumber);
		return os.meanNumber;
	}
}
