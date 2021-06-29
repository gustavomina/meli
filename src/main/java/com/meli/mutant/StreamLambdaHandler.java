package com.meli.mutant;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.meli.mutant.config.imp.MutantFinderConfig;

public class StreamLambdaHandler implements RequestStreamHandler {

	private static SpringLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;

	static {
		try {
			System.out.println("Initializing...");
			handler = SpringLambdaContainerHandler.getAwsProxyHandler(MutantFinderConfig.class);
			System.out.println("Started...");

		} catch (ContainerInitializationException cie) {
			cie.printStackTrace();
			throw new RuntimeException("Couldn´t initialize spring framework", cie);

		}
	}

	@Override
	public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {

		handler.proxyStream(input, output, context);
	}

}
